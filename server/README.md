# Server
Server-side application for Marvelous Bob game.

## To launch the server locally (for devs)
The ``.run`` folder at the root of the repository contains a few IntelliJ ``Run Configuration``.

If you open the project in IntelliJ, you will automatically have your Run Configurations set up: simply launch `[SERVER] Local`.

## AWS
Listing the services and resources used so that we can easily go back and clean everything, eventually.

**[Free-Tier](https://aws.amazon.com/free/)** started on: **17 february 2020** (it lasts 12 months)

**Region**: Canada -> **`ca-central-1`**

* ECS (for EC2, not Fargate)
  * Cluster
  * Service
  * Task Definition
  * IAM Role (created automatically by ECS)
* EC2 (On-Demand, t2.micro, AMI: ECS-Optimized Amazon Linux 2) : mostly managed by the ECS Cluster
  * Auto-Scaling Group (desired 1, min 0, max 1)
  * EBS Volume (Prevent deletion, 30GB, gp2)
  * Key Pair
  * Security Group
  * Launch Configuration
  * Elastic IP (on re-create if your instance dies, this is not configured to be associated automatically!)
* Cost Explorer -> Budget Alarm

### ECS configuration
Initial set up was done through following [this tutorial](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/getting-started-ecs-ec2.html).
 
Then, to customize the set up to work with GitHub Actions, the following changes were made:

  1. Changed the ``containerPort`` of `PortMapping` from ``80`` to ``80``.
  2. Removed the ``commands`` that was setting up a default HTML page to land on.
  3. Updated the ``entryPoint`` to match a jar execution: `java, -jar, /app.jar`.
  4. Edit the ``build.gradle`` file so that jars do not include version numbers and include Shadow plugin.
  5. Add a ``Dockerfile`` which extends our JDK (alpine version) and copies the generated jar.
  6. Added a ``workingDirectory`` value of `/`.
  7. Changed the ``image`` to point toward our Docker Hub repository.
  8. Enforced the ``requiresCompatibilities`` to be `EC2`.
  9. The minimum healthy percentage modified from ``100%`` to `0%`, and maximum from `200%` to `100%`: this is to ensure that we can kill a running task and then start a new one, and never have more than 1 task in the Container. See [this](https://stackoverflow.com/a/40741816/9768291) SO answer to understand better.

## CI/CD
Done through GitHub Actions: deploying a Docker image into our public Docker Hub repository, and updating the ECS stack subsequently.

The Dockerfile looks for the result of calling the ``shadowJar`` on this current module (``server``).

To re-use the script, you will have to pay attention to these lines:
* `DH_REGISTRY: ${{ secrets.DOCKER_USERNAME }}` : Define a `DOCKER_USERNAME` secret in your GitHub repository's Secrets (in the Settings). It should be the username you use to authenticate on DockerHub.
* `DH_REPOSITORY: marvelous-bob-server` : Replace `marvelous-bob-server` with the name of your DockerHub Repository.
* `password: ${{ secrets.DOCKER_PASSWORD }}` : Define a `DOCKER_PASSWORD` secret in your GitHub repository's Secrets (in the Settings). It should be the password you use to authenticate on DockerHub.
* `aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}` : Define a `AWS_ACCESS_KEY_ID` secret in your GitHub repository's Secrets (in the Settings). It should be the Access Key Id you were given when you created your AWS account/IAM User (it's in an Excel file).
* `aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}` : Define a `AWS_SECRET_ACCESS_KEY` secret in your GitHub repository's Secrets (in the Settings). It should be the Secret Access Key you were given when you created your AWS account/IAM User (it's in an Excel file).
* `aws-region: ca-central-1` : Replace `ca-central-1` with the AWS Region you plan on deploying your server into.
* `task-definition: utils/deploys/task-definition.json` : Only change `utils/deploys/task-definition.json` if you want the `task-definition.json` file to be positioned elsewhere in the repository.
* `container-name: marvelous-bob-ecs-td-container-name` : Replace `marvelous-bob-ecs-td-container-name` with the name of the container you linked to your AWS ECS Task Definition.
* `service: marvelous-bob-ecs-service` : Replace `marvelous-bob-ecs-service` with the name of the AWS ECS Service that is in charge of managing your AWS ECS Task Definition.
* `cluster: marvelous-bob-ecs-cluster` : Replace `marvelous-bob-ecs-cluster` with the name of the AWS ECS Cluster that manages your AWS EC2 VM.