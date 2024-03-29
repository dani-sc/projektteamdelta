# Ophthalmologist Software

## General Information
[How to set up the project](https://docs.google.com/document/d/1lm5sAUXTaW8OsEoEyh5zRrHNppZ_Nt9Q3MoOetVUHX4/edit#)

[Database SQL Scripts](https://github.com/DaGrisa/oculus-db)

[Coding Guidelines (WIP)](https://docs.google.com/document/d/1MdR_s4noBkViBPNaPP-k7w35AWLiUiYMLr6B2iBJhSs/edit#heading=h.uv4bdgdnpk03)

Always type in **meaningful and precise descriptions** when making a new **commit**!

## Branching Management

* **master**: Represents the most stable version of Oculus. Everything in here must be fully tested and runnable!
* **develop**: Contains changes that are in progress.

From the develop branch, you create (local) topic branches to work on individual features and fixes. Once your feature/fix is ready to go, you merge it into develop, at which point you can test how it interacts with other topic branches that your coworkers have merged in. Once develop is in a stable state, merge it into master.

For more details on this workflow, check out the [Branching Workflows chapter in Pro Git](http://git-scm.com/book/en/v2/Git-Branching-Branching-Workflows).
