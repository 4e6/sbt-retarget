# sbt-retarget

[![Build Status](https://travis-ci.org/4e6/sbt-retarget.svg?branch=master)](https://travis-ci.org/4e6/sbt-retarget)

Moves sbt `target` directory to different location.

## Usage

`retarget` command will create temporary directory inside `/tmp`,
create symlink from old `target` to new temporary directory,
and save new settings in `retarget.sbt` to persist them between sbt restarts.

    > retarget
    [info] Set target of scalan to /tmp/sbt_retarget_scalan_4393908734944267157
    [info] Set target of common to /tmp/sbt_retarget_common_8631514175680710519
    [info] Set target of meta to /tmp/sbt_retarget_meta_4399571167514219841
    [info] Set target of core to /tmp/sbt_retarget_core_199974457651390279
    [info] Set target of community-edition to /tmp/sbt_retarget_community-edition_5790795338431301656
    [info] Set current project to scalan (in build file:/home/dbushev/projects/scalan/scalan-ce/)
    > show target
    [info] common/*:target
    [info] 	/tmp/sbt_retarget_common_8631514175680710519
    [info] meta/*:target
    [info] 	/tmp/sbt_retarget_meta_4399571167514219841
    [info] core/*:target
    [info] 	/tmp/sbt_retarget_core_199974457651390279
    [info] community-edition/*:target
    [info] 	/tmp/sbt_retarget_community-edition_5790795338431301656
    [info] scalan/*:target
    [info] 	/tmp/sbt_retarget_scalan_4393908734944267157


`retargetRevert` will revert changes done by `retarget` command.

    > retargetRevert
    [info] Set target of scalan to /home/dbushev/projects/scalan/scalan-ce/target
    [info] Set target of common to /home/dbushev/projects/scalan/scalan-ce/common/target
    [info] Set target of meta to /home/dbushev/projects/scalan/scalan-ce/meta/target
    [info] Set target of core to /home/dbushev/projects/scalan/scalan-ce/core/target
    [info] Set target of community-edition to /home/dbushev/projects/scalan/scalan-ce/community-edition/target
    sho[info] Set current project to scalan (in build file:/home/dbushev/projects/scalan/scalan-ce/)
    > show target
    [info] common/*:target
    [info] 	/home/dbushev/projects/scalan/scalan-ce/common/target
    [info] meta/*:target
    [info] 	/home/dbushev/projects/scalan/scalan-ce/meta/target
    [info] core/*:target
    [info] 	/home/dbushev/projects/scalan/scalan-ce/core/target
    [info] community-edition/*:target
    [info] 	/home/dbushev/projects/scalan/scalan-ce/community-edition/target
    [info] scalan/*:target
    [info] 	/home/dbushev/projects/scalan/scalan-ce/target


### Settings

- retargetBaseDirectory
- retargetOrigin
- retargetPrefix
- retargetConfigFile
- retargetCopyFiles
