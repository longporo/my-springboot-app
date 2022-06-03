#! The default profile is dev，for another like prod, just type behind the command：sh build.sh prod

profileActive=dev
if [ -n "$1" ]; then
    profileActive=$1
fi

mvn clean package -Dmaven.test.skip=true -P$profileActive