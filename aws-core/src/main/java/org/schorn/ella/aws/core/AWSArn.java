package org.schorn.ella.aws.core;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.config.model.ResourceType;


public class AWSArn {


    public enum Platform {
        AWS;
        @Override
        public String toString() {
            return super.name().toLowerCase();
        }
    }

    /**
     *
     */
    public enum Service {
        S3(ResourceType.AWS_S3_BUCKET),
        SNS(ResourceType.AWS_SNS_TOPIC),
        SQS(ResourceType.AWS_SQS_QUEUE),
        LAMBDA(ResourceType.AWS_LAMBDA_FUNCTION),
        RDS(ResourceType.AWS_RDS_DB_INSTANCE),
        //SEARCH(ResourceType.AWS_ELASTICSEARCH_DOMAIN),
        NA(null);

        private final ResourceType resourceType;
        Service(ResourceType resourceType) {
            this.resourceType = resourceType;
        }

        public ResourceType resourceType() {
            return this.resourceType;
        }

        @Override
        public String toString() {
            return super.name().toLowerCase();
        }

        static public Service of(ResourceType resourceType) {
            for (Service service : Service.values()) {
                if (service.resourceType.equals(resourceType)) {
                    return service;
                }
            }
            return Service.NA;
        }
        static public Service of(String name) {
            for (Service service : Service.values()) {
                if (service.name().equalsIgnoreCase(name)) {
                    return service;
                }
            }
            return Service.NA;
        }
    }

    /**
     * Constructor
     *
     * @return
     */
    static public AWSArn get(ResourceType resourceType, String resourceName) {
        AWSConfig awsConfig = AWSConfig.getInstance();

        Platform platform = null;
        if (awsConfig.has("AWS_PLATFORM")) {
            platform = Platform.valueOf(awsConfig.getString("AWS_PLATFORM").get().toUpperCase());
        }
        platform = platform == null ? Platform.AWS : platform;

        Region region = null;
        if (awsConfig.has("AWS_REGION")) {
            region = Region.of(awsConfig.getString("AWS_REGION").get().toUpperCase());
        }
        region = region == null ? Region.US_EAST_1 : region;

        String account = "";
        if (awsConfig.has("AWS_ACCOUNT_ID")) {
            account = awsConfig.getString("AWS_ACCOUNT_ID").get();
        }
        return new AWSArn(platform, resourceType, region, account, resourceName);
    }

    private final Platform platform;
    private final ResourceType resourceType;
    private final Service service;
    private final Region region;
    private final String account;
    private final String resourceName;
    private final String arn;

    /**
     *
     * @param platform
     * @param resourceType
     * @param region
     * @param account
     * @param resourceName
     */
    private AWSArn(Platform platform, ResourceType resourceType, Region region, String account, String resourceName) {
        this.platform = platform;
        this.resourceType = resourceType;
        this.service = Service.of(resourceType);
        this.region = region;
        this.account = account;
        this.resourceName = resourceName;
        String arn0 = null;
        switch (this.service) {
            case S3:
            case SNS:
            case SQS:
            case RDS:
                arn0 = String.format("arn:%s:%s:%s:%s:%s",
                        this.platform.toString(),
                        this.service.toString(),
                        this.region.toString(),
                        account,
                        resourceName);
                break;
            case LAMBDA:
                arn0 = String.format("arn:%s:%s:%s:%s:function:%s",
                        this.platform.toString(),
                        this.service.toString(),
                        this.region.toString(),
                        account,
                        resourceName);
                break;
            default:
                break;
        }
        this.arn = arn0;
    }

    public Region getRegion() {
        return region;
    }

    public Service getService() {
        return service;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public String getAccount() {
        return this.account;
    }

    @Override
    public String toString() {
        return this.arn;
    }


}
