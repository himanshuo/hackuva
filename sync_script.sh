#!/bin/bash -e

#Update first, done to avoid conflicts
git pull

#Now push changes
git add --all
date=`date`
echo $date
git commit -m "Auto commit at $date"
git push -u origin master
