package tech.nocountry.c1832ftjavaangular.service;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    public Geometry getGeometry(String latitude, String longitude) throws ParseException {
        WKTReader wktReader = new WKTReader();

        return wktReader.read("POINT (+%s %s)".formatted(latitude, longitude));
    }
}
