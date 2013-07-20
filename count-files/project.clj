(defproject count-files "0.1.0"
  :description "Count files in a directory tree, grouped by their extension"
  :url "https://github.com/olange/toolbox/tree/master/count-files"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.5.1"]
    [org.clojure/tools.cli "0.2.2"]
    [me.raynes/fs "1.4.4"]
   ]
  :main count-files.core)
