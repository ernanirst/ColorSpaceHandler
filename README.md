# Color Space Handler
Java library with a bunch of color space operations

## Features

* YUV image handlig;
* Conversion of YUV > RGB > YUV;

* /api - Serverless REST API that serves as an interface between front-end and the database.
* /static - web interface.
* /sql - SQLs used to create and test the database's tables

# Deploy
## Requirements
* Install [Serverless Framework](https://serverless.com/framework/docs/providers/aws/guide/installation/) 
* Configure [Amazon Web Services](https://serverless.com/framework/docs/providers/aws/guide/credentials/) for Serverless
## Steps
* `git clone https://bitbucket.org/mariobaldini/machinebase-api.git`
* `cd machinebase-api/api/`
* `chmod +x config.sh`
* `./config.sh`
* `sls deploy`
