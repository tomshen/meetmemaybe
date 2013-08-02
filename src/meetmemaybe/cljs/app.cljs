(ns app)

(set! (.-innerHTML (.getElementById js/document "container"))
      "This has been manipulated by ClojureScript!")