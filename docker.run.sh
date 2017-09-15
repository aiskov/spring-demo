#!/usr/bin/env bash

function print_fail {
    printf "\e[31mDOCKER STARTING FAIL!\e[0m\n" 1>&2
}

printf "\e[1mDOCKER STARTING...\e[0m\n"

PROFILE=env/${1:-develop}
COMPOSE_FILE=docker-compose.yml

# Check if configuration directory exist
if [ ! -f "${PROFILE}/${COMPOSE_FILE}" ]; then
    printf "\e[31mConfiguration "${PROFILE}"/${COMPOSE_FILE} not exist\e[0m\n" 1>&2
    exit 1;
fi

printf "Docker compose file: \e[4m${PROFILE}/${COMPOSE_FILE}\e[0m\n"
cd ${PROFILE}

# Stop old containers
printf "\e[1mStop old containers\e[0m\n"
docker-compose -f ${COMPOSE_FILE} stop || { print_fail; exit 1; }

# Remove old containers
printf "\e[1mRemoving old containers\e[0m\n"
docker-compose -f ${COMPOSE_FILE} rm --force || { print_fail; exit 1; }

# Remove old containers
printf "\e[1mStarting containers\e[0m\n"
docker-compose -f ${COMPOSE_FILE} up -d || { print_fail; exit 1; }

printf "\n\e[1mDOCKER STARTING DONE!\e[0m\n"
