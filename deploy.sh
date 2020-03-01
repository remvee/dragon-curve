set -ex

lein cljsbuild once

rm -rf deploy
git clone git@github.com:remvee/dragon-curve.git deploy
cd deploy

git checkout -b gh-pages --track origin/gh-pages

rm -rf -- *
mkdir js
cp ../resources/public/index.html .
cp ../resources/public/js/app.js js/

git add -- *
git commit -m '..'
git push
