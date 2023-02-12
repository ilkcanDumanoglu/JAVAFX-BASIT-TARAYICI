//gerekli dahil edilmeler yapıldı.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    //bu fonksiyon istisnaları göz ardı eder
    @Override
    public void start(Stage primaryStage) throws Exception{
        // javafx formatındaki xml dosyayı okur. 
        Parent root= FXMLLoader.load(getClass().getResource("sekmeler.fxml"));
        //içinde root objesi de bulunan bir sahne objesi oluşur
        Scene scene = new Scene(root);
        //resim objesi oluşturur.
        Image image = new Image("C:/Users/ilkca/OneDrive/Masaüstü/Yazilim/WEB/Tema2/FOTOLAR/logo4.png");

        //zemine resmimizi ekler(sol üst)
        primaryStage.getIcons().add(image);
        //css dosyamızı proje ekler
        scene.getStylesheets().add(this.getClass().getResource("anasayfa.css").toExternalForm());
        //sayfanın başlığı ayarlanır.
        primaryStage.setTitle("İKO");
        //zemine sahne eklenir
        primaryStage.setScene(scene);
        //zemini gösterir
        primaryStage.show();
    }

    //main fonksiyon çalışır
    public static void main(String[] args) {
        launch(args);
    }
}
