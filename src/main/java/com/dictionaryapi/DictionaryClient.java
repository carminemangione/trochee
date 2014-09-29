package com.dictionaryapi;

import com.dictionaryapi.dto.EntryList;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStreamReader;
import java.io.Reader;

public class DictionaryClient {

    public static void main(String[] args) throws Exception {
        final String word = "doggy";
        final EntryList lookup = new DictionaryClient(Client.create()).lookup(word);
        System.out.println(lookup.resolveIsTrochee(word));
    }

    private final Client client;
    private final JAXBContext jaxbContext;
    private final ThreadLocal<Unmarshaller> unmarshallers = new ThreadLocal<Unmarshaller>() {
        @Override
        protected Unmarshaller initialValue() {
            try {
                return jaxbContext.createUnmarshaller();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public DictionaryClient(Client client) {
        this.client = client;
        try {
            jaxbContext = JAXBContext.newInstance(EntryList.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public EntryList lookup(String word) throws Exception {

        final WebResource wordLookupPath = client.resource("http://www.dictionaryapi.com")
                .path("/api/v1/references/collegiate/xml")
                .path(word)
                .queryParam("key", "29e74ebd-b417-4d61-8a06-52bfedb5af8d");
        final ClientResponse response = wordLookupPath.get(ClientResponse.class);
        try (Reader r = new InputStreamReader(response.getEntityInputStream())) {
            return (EntryList) unmarshallers.get().unmarshal(r);
        } finally {
            response.close();
        }

    }
}
