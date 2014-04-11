Create a new repository on the command line

touch README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/himanshuo/hackuva.git
git push -u origin master


just to update a README.md
git add README.md                    //note: you can use git add --a for all files
git commit -m "first commit"
git push -u origin master



to delete your updates and just get whatever is on git
git fetch origin
git reset --hard origin/master
git pull

