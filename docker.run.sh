#!/usr/bin/env bash

function print_fail {
    printf "\e[31mApplication start fail!\e[0m\n" 1>&2
}

printf "\e[1mStarting Application...\e[0m\n"

PROFILE=env/${1:-develop}
COMPOSE_FILE=docker-compose.yml
APP_NAME=demo-app

# Check if configuration directory exist
if [ ! -f "${PROFILE}/${COMPOSE_FILE}" ]; then
    printf "\e[31mConfiguration "${PROFILE}"/${COMPOSE_FILE} not exist\e[0m\n" 1>&2
    exit 1;
fi

printf "Docker compose file: \e[4m${PROFILE}/${COMPOSE_FILE}\e[0m\n"
cd ${PROFILE}

printf "\e[1mStarting containers\e[0m\n"
docker-compose -p ${APP_NAME} -f ${COMPOSE_FILE} up -d || { print_fail; exit 1; }

printf "\n\e[1mApplication started!\e[0m\n"
