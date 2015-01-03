(defproject dragon_curve "1.0.0-SNAPSHOT"
  :description "Draw a dragon curve"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371" :scope "provided"]
                 [reagent "0.4.2"]]

  :plugins [[lein-cljsbuild "1.0.3"]]

  :cljsbuild {:builds {:app {:source-paths ["src"]
                             :compiler {:preamble ["reagent/react.js"]
                                        :output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js/app"
                                        :optimizations :none}}}})
