module ia_demo.soccer {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.sql;
    exports soccer;
    exports soccer.model;
    exports soccer.pages;
}
