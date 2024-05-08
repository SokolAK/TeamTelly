package pl.sokolak.teamtally.backend.util;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ImageUtil {

    public static Image createUserPhotoAsImageSmall(byte[] photo) {
        Image image = createUserPhotoAsImage(photo);
        image.addClassName("user-photo-small");
        return image;
    }

    public static Image createUserPhotoAsImageMedium(byte[] photo) {
        Image image = createUserPhotoAsImage(photo);
        image.addClassName("user-photo-medium");
        return image;
    }

    private static Image createUserPhotoAsImage(byte[] photo) {
        return photo != null ?
                convertBase64ToImage(photo) :
                convertBase64ToImage(getDefaultUserPhoto());
    }

    public static String createUserPhotoAsBase64(byte[] photo) {
        return photo != null ?
                getImageAsBase64(Base64.getDecoder().decode(photo)) :
                getImageAsBase64(Base64.getDecoder().decode(getDefaultUserPhoto()));
    }

    private static Image convertBase64ToImage(byte[] image) {
        ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(image));
        StreamResource resource = new StreamResource("image.jpg", () -> bis);
        return new Image(resource, "");
    }

    private static String getImageAsBase64(byte[] image) {
        return "data: image/png; base64," + Base64.getEncoder().encodeToString(image);
    }

    private static byte[] getDefaultUserPhoto() {
        String photo = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxIPEBASExEQEBEQEhAQDxIPDxAQEhIVFRIWFhUSFRMYHSggGBolHRUTITEh" +
                "JSkrLi4uFx8zODMtNygtLisBCgoKBQUFDgUFDisZExkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKys" +
                "rKysrKysrK//AABEIAOEA4QMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAAAwUCBAYBB//EADUQAAIBAQYDBQgCAgMBAA" +
                "AAAAABAgMEBREhMVESQXFhgaGxwRMiMkJSYpHhctHw8RSCkjP/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/8QAFBEBAAAAAAAAAAAAA" +
                "AAAAAAAAP/aAAwDAQACEQMRAD8A+4gAAAAAAAA8bK+1XrGOUffe/wAv55gWOJqVrxpw+bie0c/0Ulotc6nxSy2WS/BrgWtW+X8s" +
                "Uu2Tx8Eas7xqv5sP4pI1QBJKvN6yk/8AsyNsAAmZxrSWkpLpJmAA2YXhVXzt9cGbVK+ZL4op9MUysAHQULzpy58L+7Lx0NxPE5M" +
                "loWiUPhk12ar8AdQCrst7p5TXC91p37FnGSaxTTT0aA9AAAAAAAAAAAAAAAAILVao01jJ9EtWQ2+3KksFnJ6LbtZQ1ajk22229w" +
                "J7XbpVOyP0r13NYAAAAAAAAAAAAAAAAAAT2W1ypv3Xlzi9GQADo7HbY1VllLnF6925tHJxk0008GtGi7u68ePCMspcnyl+wLEAA" +
                "AAAAAAAADSvG2qksFnN6LbtZNbLQqcXJ9Et2c3VqOTcnm3qB5OTbbbxbzbZ4AAAAAAAAAAAAAAAAAAAAAAAAABeXXb+P3ZfEtH9" +
                "X7LE5KLweKyazR0V32v2sfuWUl6gbYAAAAAeNnpXXzaOGPCtZ69OYFZeFq9pP7VlH++81gAAAAAAAAAAAAAAAAAAAAAAAAAAAAE" +
                "tltDpyUl3rdbEQA6unNSSazTzRkVFyWjWm+sfVFuAAAHjOattf2k5S5aLoi7vStwUpbv3V3/rE50AAAAAAAAAAABnSoyn8Kx8vy" +
                "bNisXH70so8lzf6LSMUlglglsBX07s+qXcl6kyu6H3PvNsAacruh9y7yCrdrXwtPseTLMAUFSm4vBpp9piX9WkpLBrH06FRa7K6" +
                "b3i9H6MDXAAAAAAAAAAGdGo4SUlqmn+jqKc1JJrRpNd5yheXLWxg484Pwea9QLEAAU1+1M4R2Tk/JepVm1ek8asuzBfhf3iaoAAA" +
                "AAAAAA2bDZ+OWfwrXt2Rql5Y6XBBLm831YEwAAAAAAAB5OCkmnmnqegCitNFwk1+HuiMtbzpYw4ucfJlUAAAAAAAAAN+5amFTD6k" +
                "13rP+zQJbLPhnB7SXnmB04PQBy1oljOb3lLzIw2AAAAAAAAAM6EcZRW7XmX5R2P/wCkOpdgAAAAAAAAAABjUjjFrdNHPnRHPMAA" +
                "AAAAAAAAeAdD/wAtbgpfbHoEDQM68cJyW0pLxMAAAAAAAAAMqUsJJ7NPxL850ubBW4oLeOT9GBsgAAAAAAAAADCvPhjJ7JlAWl61" +
                "cEo75votCsAAAAAAAAAHh6eATezYLj/hoAVd5wwqz7Xj+UaxZ37T96Mt1g+7/ZWAAAAAAAAACayV/Zyx5aNdhCAOghJNJp4p6HpS" +
                "2W1On2x5r1RbUa8ZrFPHs5ruAkAAAAADCvVUFi/99hhaLVGGub5Ja/oqLRXc3i+5ckBjVqOTber/AMwMQAAAAAAAAABJZ4cU4reS" +
                "XiRm7c9Piqp/Sm/ReYHQAADSvajxUnvH3vxr4YnPnWSWJzFpo8E5R2eXTkBEAAAAAAAADOjRlN4JY+S6lnZ7BGOcvefh+AKylRlP" +
                "4U35fk3KV3S1cuH+OLf5LJADGnBpZycu14GQAAir0pS0m49EvPUlAFTVu+a0wl0eD8TVlBp4NNPtR0BjUpqSwaTXaBQAsLRd3OH" +
                "/AJfoyvawyeTAAAAAAAAAF3cdLCDl9TwXRfvEpYQcmktW0l3nU0KahFRXJJAZgAAVd92fFKa1jlLpuWh5KKaaeaawYHJgntlndO" +
                "bjy1i90QAAAANiyWR1HjpHm9+xCx2b2j+1av0LiMUkksktEB5TpqKwSwRkAAAAAAAAAAAAAgtVlVRbPk/73JwBQ1qTg8Hr59DAvLTQ" +
                "VRYPXk9ilqQcW09UBiAAABnQpOclFavw7QLC5LPi3N6LKPXmy6I6FJQiorREgAAAAABq2+y+1jh8yzi/Q52UWm08msmjrCuvOwca4o" +
                "/EtV9X7AozKnByaS1ZiWN1UcnN88l6gbtGkoRUVy8d2ZgAAAAAAAAAAAAAAAAADUvGz8UeJfFHxRtgDngT22jwTezzX9GuB6X912P2c" +
                "cX8UtexbGvdVg0nJfxT82WwAAAAAAAAAAAV15Xdx+9HKXNcpfszpw4Uo7LA3jCpTx6gawPZRaPAAAAAAAAAAAAAAAAAAB6liBpXnSx" +
                "gnzi/P/EZXbduGEpr+MfVllClh1JAAAAAAAAAAAAAAAAAPGsSKdHYmAGo1geG20YOiugGuCR0n1MXF7AYgAAAAAPVF7GapMCM9SJo0" +
                "VzJFHACGNHcmjHA9AAAAAAAAAAAAAAAAAAAAAAAAAAAARzIWAB4iemeACRA9AAAAAAAAAAAAAAAAAH/2Q==";
        return photo.getBytes();
    }
}
