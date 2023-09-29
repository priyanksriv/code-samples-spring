function note() {
    local GREEN NC
    GREEN='\033[0;32m'
    NC='\033[0m' # No Color
    printf "\n${GREEN}$@  ${NC}\n" >&2
}

set -e


note "Building Service...";		./gradlew clean build;

note "Running Service...";     	nohup java -jar -Xms256m -Xmx512m build/libs/*-SNAPSHOT.jar &

note "Service running on http://localhost:8080/";
note "Swagger API docs on http://localhost:8080/swagger-ui.html";
