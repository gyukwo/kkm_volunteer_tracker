module ia_demo.kkm {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.sql;
    exports kkm;
    exports kkm.model;
    exports kkm.pages;
    requires org.apache.pdfbox;
}
