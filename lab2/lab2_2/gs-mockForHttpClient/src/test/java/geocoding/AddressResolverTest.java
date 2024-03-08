package geocoding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import connection.ISimpleHttpClient;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    AddressResolverService resolver;
    ISimpleHttpClient httpClient;

    @BeforeEach
    void start(){
        httpClient = mock(ISimpleHttpClient.class);
        resolver = new AddressResolverService(httpClient);
    }

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        //todo: implement test; remove Disabled annotation

        String response = "{\"info\": {\"statuscode\": 0,\"copyright\": {\"text\": \"© 2024 MapQuest, Inc.\",\"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\": \"© 2024 MapQuest, Inc.\"},\"messages\": []},\"options\": {\"maxResults\": 1,\"ignoreLatLngInput\": false},\"results\": [{\"providedLocation\": {\"latLng\": {\"lat\": 40.63436,\"lng\": -8.65616}},\"locations\": [{\"street\": \"Avenida da Universidade\",\"adminArea6\": \"Aveiro\",\"adminArea6Type\": \"Neighborhood\",\"adminArea5\": \"Aveiro\",\"adminArea5Type\": \"City\",\"adminArea4\": \"Aveiro\",\"adminArea4Type\": \"County\",\"adminArea3\": \"\",\"adminArea3Type\": \"State\",\"adminArea1\": \"PT\",\"adminArea1Type\": \"Country\",\"postalCode\": \"3810-489\",\"geocodeQualityCode\": \"B1AAA\",\"geocodeQuality\": \"STREET\",\"dragPoint\": false,\"sideOfStreet\": \"L\",\"linkId\": \"0\",\"unknownInput\": \"\",\"type\": \"s\",\"latLng\": {\"lat\": 40.63437,\"lng\": -8.65625},\"displayLatLng\": {\"lat\": 40.63437,\"lng\": -8.65625},\"mapUrl\": \"\"}]}]}"
;
        
        // will crash for now...need to set the resolver before using it
        when(httpClient.doHttpGet(anyString())).thenReturn(response);
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        ///todo: implement test


        Optional<Address> result = resolver.findAddressForLocation( -361,-361); 
        // verify no valid result
        assertFalse( result.isPresent());

    }
}