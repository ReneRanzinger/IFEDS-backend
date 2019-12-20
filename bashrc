if [ -f ~/.secrets ]; then
    . ~/.secrets
fi

if [ -f ~/workspace/IFEDS-backend/scripts/createVolumes.sh ]; then
    . ~/workspace/IFEDS-backend/scripts/createVolumes.sh
fi

export SITE_CODE=ifeds-server
export IFEDS_NETWORK=ifeds-network
export POSTGRES_USER=ifeds
