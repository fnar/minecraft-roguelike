echo Building all Roguelike Dungeons modules...

pushd roguelike-core\
  call scripts\build.bat
popd

pushd 1.12\
  call scripts\build.bat
popd

pushd 1.14\
  call scripts\build.bat
popd
