package ru.konstantinpetrov;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import lombok.Data;

public class CrptApi {

    private final String URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";



    public CrptApi(TimeUnit timeUnit, int requestLimit) {
    }


    public static void main(String[] args) {

        CrptApi crptApi=new CrptApi(TimeUnit.MILLISECONDS, 1000);

        Product product1 = new Product("aaa", new Date(2012-12-12), "111", "aaa", "aaa", new Date(2012-12-12), "aaa", "aaa", "aaa");
        Product product2 = new Product("bbb", new Date(2011-11-11), "222", "bbb", "bbb", new Date(2011-11-11), "bbb", "bbb", "bbb");

        Description description = new Description("rrr");


        List<Product> product_list = new ArrayList<>();
        product_list.add(product1);
        product_list.add(product2);

        Document document = new Document(description, "ccc", "ccc", "ccc", true, "ccc", "ccc", "ccc", new Date(2010-10-10), "ccc", product_list, new Date(2009-9-9), "ccc");

        String signature = "Специальная подпись";
        // this.description=description;
        // this.signature=signature;
        // CrptApi crptApi=new CrptApi(TimeUnit.MILLISECONDS, 1000);

        crptApi.createDocument(document, signature);
    }

    public void createDocument(Document document, String signature){
        
        ResponseDTO responseDTO = new ResponseDTO(document, signature);

        Gson gson = new Gson();
        String jsonDocument = gson.toJson(responseDTO);

        

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(URL);
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity stringEntity = new StringEntity(jsonDocument, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int executeStatusCode = response.getStatusLine().getStatusCode();
                if (executeStatusCode == 200) {
                    System.out.println("Document was created");
                } else {
                    System.out.println("Error to create a document\n Code error: " + executeStatusCode);
                }            
            }catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    

    }

    @Data
    public class ResponseDTO{
        private Document document;
        private String signature;

        public ResponseDTO(Document document, String signature) {
            this.document=document;
            this.signature=signature;
        }
    }

    @Data
    public static class Document {
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private Date production_date;
        private String production_type;
        private List<Product> products;
        private Date reg_date;
        private String reg_number;

        public Document(Description description, String doc_id, String doc_status, String doc_type, boolean importRequest, String owner_inn, String participant_inn, String producer_inn, Date production_date, String production_type, List<Product> products, Date reg_date, String reg_number) {
            this.description = description;
            this.doc_id = doc_id;
            this.doc_status = doc_status;
            this.doc_type = doc_type;
            this.importRequest = importRequest;
            this.owner_inn = owner_inn;
            this.participant_inn = participant_inn;
            this.producer_inn = producer_inn;
            this.production_date = production_date;
            this.production_type = production_type;
            this.products = products;
            this.reg_date = reg_date;
            this.reg_number = reg_number;
        }   
    }

    @Data
    public static class Description{
        private String participantInn;

        public Description(String participantInn) {
            this.participantInn = participantInn;
        }
    }

    @Data
    public static class Product{
        private String certificate_document;
        private Date certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private Date production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        public Product(String certificate_document, Date certificate_document_date, String certificate_document_number, String owner_inn, String producer_inn, Date production_date, String tnved_code, String uit_code, String uitu_code) {
            this.certificate_document = certificate_document;
            this.certificate_document_date = certificate_document_date;
            this.certificate_document_number = certificate_document_number;
            this.owner_inn = owner_inn;
            this.producer_inn = producer_inn;
            this.production_date = production_date;
            this.tnved_code = tnved_code;
            this.uit_code = uit_code;
            this.uitu_code = uitu_code;
        }
    }
}