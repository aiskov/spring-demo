#!/usr/bin/env bash

# Functions
function print_fail {
    printf "\e[31mProcessing fail!\e[0m\n" 1>&2
}

# Configuration
PROFILE=env/develop
COMPOSE_FILE=docker-compose.yml
APP_NAME=demo-app

START=true
REMOVE=false
STOP=false

# Operation
while test $# -gt 0
do
    case "${1}" in
        --recreate)
            STOP=true
            REMOVE=true
            ;;
        --stop)
            START=false
            STOP=true
            ;;
        --remove)
            REMOVE=true
            ;;
        -*)
            printf "\e[31mBad parameter ${1}\e[0m\n" 1>&2
            exit 1
            ;;
        *)
            if [[ "${1}" != -* ]] ; then
                PROFILE=env/${1}
            fi
            ;;
    esac
    shift
done

# Check if configuration directory exist
if [ ! -f "${PROFILE}/${COMPOSE_FILE}" ]; then
    printf "\e[31mConfiguration "${PROFILE}"/${COMPOSE_FILE} not exist\e[0m\n" 1>&2
    exit 1
fi

printf "Docker compose file: \e[4m${PROFILE}/${COMPOSE_FILE}\e[0m\n"
cd ${PROFILE}

if [ "${STOP}" = true ] ; then
    # Stop old containers
    printf "\e[1mStop old containers\e[0m\n"
    docker-compose -p ${APP_NAME} -f ${COMPOSE_FILE} stop || { print_fail; exit 1; }
    printf "\e[1mContainers stopped\e[0m\n"
fi

if [ "${REMOVE}" = true ] ; then
    # Remove old containers
    printf "\e[1mRemoving old containers\e[0m\n"
    docker-compose -p ${APP_NAME} -f ${COMPOSE_FILE} rm --force || { print_fail; exit 1; }
fi

if [ "${START}" = true ] ; then
    # Start new containers
    printf "\e[1mStarting containers\e[0m\n"
    docker-compose -p ${APP_NAME} -f ${COMPOSE_FILE} up -d || { print_fail; exit 1; }
    printf "\n\e[1mApplication started!\e[0m\n"
fi