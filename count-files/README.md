# Count files

[API docs](https://github.com/olange/toolbox/blob/master/count-files/doc/uberdoc.html)

Count files in a directory tree, grouped by their extension.

## Installation

Build it from the sources with [Leiningen](http://leiningen.org); you'll find
the packaged JAR in the `target/` subfolder:

    $ git clone git@github.com:olange/toolbox.git
    $ cd toolbox/count-files
    $ lein uberjar

## Usage

Given a base directory path, the utility counts all files it finds therein,
grouped by their extension. 

    $ java -jar count-files-0.1.0-standalone.jar [options] basedir

Alternatively you can use [Leiningen](http://leiningen.org) to run the utility:

    $ lein run -- [options] basedir

## Options

* `-h`, `--help`: displays help and sample usage
* `-w`, `--walk`: walks immediate children directories and counts the files
  within each of them; if not specified, count starts within the base directory.

## Example

Count files of the current directory:

    $ java -jar count-files-0.1.0-standalone.jar --walk .

    Counting files per extension within directory tree .../toolbox/count-files
    .../toolbox/count-files/doc/:     {".md" 1}
    .../toolbox/count-files/src/:     {".clj" 1}
    .../toolbox/count-files/target/:  {".dependencies" 1}
    .../toolbox/count-files/test/:    {".clj" 1}

### Improvements

* Children directories might be walked in depth with the option --walk;
  the utility currently walks only immediate children directories.

## License

Copyright © 2013 Le Petit Atelier de Génie logiciel, Olivier Lange

Distributed under the Eclipse Public License, the same as Clojure.
