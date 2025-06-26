# A clone of Meta's messenger built using spring boot and react

## Run (when run locally, look at `http://localhost:3000`)
### Dependencies:
* node, npm
* jdk 17
* maven
* docker
* docker compose
* python3

# Compile, test and build docker image with the backend:
```shell
mvn clean install
```
# Run the backend with docker
```shell
docker compose up
```
# Run frontend
```shell
cd messenger-frontend
npm install
npm run start
```
# Generate javadoc (look in `./target/reports/apidocs/`)
```
mvn javadoc:javadoc
```
# Run e2e tests in selenium (example includes venv creation)
```
cd selenium-e2e
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
sh run.sh
```
