#!/bin/bash -e

#Update first, done to avoid conflicts
git pull

#Remove caches and superfluous files
rm -rf __pycache__/ *~ *#

#Now push changes
git add --all
date=`date`
echo $date
git commit -m "Auto commit on $date"
git push -u origin master
