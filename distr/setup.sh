#!/bin/bash

type=$1
set -x
sudo systemctl stop snappy-autopilot.timer
sudo systemctl disable snappy-autopilot.timer
distr="/home/ubuntu/distr"

if [[ $type = "mgmt"  ]]
then 

sudo snappy install --allow-unauthenticated $distr/*.snap
systemctl | grep subutai-mng_subutai-mng | awk -F" " '{print $1}' | awk -F"." '{print " sudo systemctl stop "$1}'  | bash
sudo find /var/lib/apps/subutai-mng  -name db -type d | awk '{print "sudo rm -rf "$1}' | bash
sudo bash -c "echo 'Defaults secure_path="/apps/bin/:/apps/subutai/current/bin:/apps/subutai-mng/current/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"' > /etc/sudoers.d/subutai"

else

sudo snappy install --allow-unauthenticated $distr/*.snap
sudo bash -c "echo 'Defaults secure_path="/apps/subutai/current/bin:/apps/subutai-mng/current/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"' > /etc/sudoers.d/subutai"
sudo btrfsinit /dev/sdc

fi
