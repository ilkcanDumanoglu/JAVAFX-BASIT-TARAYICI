//gerekli dahil edilmeler yapıldı.
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import java.util.*;

//controller classı oluşturuldu ve "Initializable" interface'i dahil edildi.
public class sekmecontroller implements Initializable{
    //Sahneyi oluştururken hazırlamış olduğumuz xml dosyasında kullanılan elemanları kontrol edebilmek için onların 'id' leri  
    //ile aynı isimde bir obje oluşturuyoruz ve bunları xml dosyasında bulunduğunu belli etmek için @FXML etiketini kullanıyoruz.
    @FXML
    private Label baslik;
    @FXML
    private TextField arama,arama1;
    @FXML
    private WebView webView;
    @FXML
    private Button aButon,kButon;
    @FXML
    private TextArea bilgi;
    @FXML
    private Tab anatab, sekmetab;
    @FXML
    private TabPane tabpane;


    //kullanacağımız bazı değişken ve objeleri oluşturuyoruz.
    private WebHistory history;
    private WebEngine engine;
    private double webZoom;
    Timer timer = new Timer();
    int millis = 200;

    //tarayıcımızı başlatırken yapılacak olan işlemleri ayarlıyoruz.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //webView ile chromium tabanlı bir arama motoru oluşturuyoruz.
        engine= webView.getEngine();
        //webView bölümünü büyütürken ve küçültürken kullanacagız (1 = 100%).
        webZoom=1;
        //eklediğimiz bilgi alanını normalde görünmemesi için görünmez yapıyoruz.
        bilgi.setVisible(false);
        //sayfamızı yükleme fonksiyonunu çağırıyoruz.
        loadPage();
    }

    public void loadPage(){
        //arama isimli TextField objesinden okunan değeri bir Stringde tutuyoruz. 
        //Daha sonra bu değer boşsa sayfayı yeniliyor, dolu ise istenilen adrese gidiyoruz.
        String aramaniz= arama.getText();
        if (aramaniz.equals("")){
            refreshPage();
        }else{
            engine.load("http://"+ aramaniz);
        }
    }

    public void al(){
        //arama1 isimli TextField objemizden(ANA sekmesinde bulunan) değer okuyoruz. 
        //Bunu arama objemize yazıyor, sekme değiştiriyor ve sayfayı yüklüyoruz.
        String e = arama1.getText();
        arama.setText(e);
        tabpane.getSelectionModel().select(sekmetab);
        loadPage();
    }
    //sayfayı yeniliyor.
    public void refreshPage(){
        engine.reload();
    }
    //5.00 dan fazla yaklaşmamak şartıyla sayfayı büyütüyor ve belirtilen milisaniye degeri kadar bilgi yazısını ekranda tutuyor.
    public void zoomIn(){
        if (webZoom<5.00){webZoom+=0.25;}
        //webzoom değeri kadar zoom yapar
        webView.setZoom(webZoom);
        bilgi.setVisible(true);
        bilgi.setText(Double.toString(webZoom));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bilgi.setVisible(false);
            }
          }, millis);
        
    }
    //0.25 den fazla uzaklaşmamak şartıyla sayfayı küçültüyor ve belirtilen milisaniye degeri kadar bilgi yazısını ekranda tutuyor.
    public void zoomOut(){
        if(webZoom>0.25){webZoom-=0.25;}
        //webzoom değeri kadar zoom yapar
        webView.setZoom(webZoom);
        bilgi.setVisible(true);
        bilgi.setText(Double.toString(webZoom));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bilgi.setVisible(false);
            }
          }, millis);
    }
    //geçmiş
    public void displayhistory(){
        //arama motoru geçmişini history degerinde saklar
        history= engine.getHistory();
        //bunu javafx e özel olan bir liste türüne çevirir ve bu listeyi bir değişkende tutar.
        ObservableList<WebHistory.Entry> entries= history.getEntries();
        //bu listedeki elemanları tek tek alıyoruz, ziyaret tarihin ve sayfa urlsini yazıyoruz.
        for (WebHistory.Entry entry: entries){
            System.out.println(entry.getLastVisitedDate()+" | "+entry.getUrl());
        }
    }
    //listemizde bir eleman geri gidiyor ve bulunduğumuz elemanı yazıyoruz.daha sonra bu elemanın urlsini arama çubuguna yazıyoruz.
    public void back(){
        history= engine.getHistory();
        ObservableList<WebHistory.Entry> entries= history.getEntries();
        history.go(-1);
        arama.setText(entries.get(history.getCurrentIndex()).getUrl());
    }
    //listemizde bir eleman ileri gidiyor ve bulunduğumuz elemanı yazıyoruz.daha sonra bu elemanın urlsini arama çubuguna yazıyoruz.
    public void forward(){
        history= engine.getHistory();
        ObservableList<WebHistory.Entry> entries= history.getEntries();
        history.go(1);
        arama.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    //ilkcan ve youtube fonksiyonları ile bu id lerdeki butonlarımıza js fonksiyonları 
    //ekliyoruz. şu an yazmakta olan fonksiyon butona basıldıgında aşağıda yazan linki açar.

    public void ilkcan(){
        engine.executeScript("window.location = \"http://ilkcandumanoglu.com\";");
    }
    public void youtube(){
        engine.executeScript("window.location = \"http://youtube.com\";");
    }

}
