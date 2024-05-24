package tech.nocountry.c1832ftjavaangular.service;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;

public interface LocationService {
    Geometry getGeometry(String latitude, String longitude) throws ParseException;
}
