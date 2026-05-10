package com.upc.pre.urbanvoiceapp.location;

import com.upc.pre.urbanvoiceapp.models.Location;
import com.upc.pre.urbanvoiceapp.repositories.LocationRepository;
import com.upc.pre.urbanvoiceapp.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    private LocationRepository locationRepository;
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = new LocationService(locationRepository);
    }

    @Test
    void whenObtainDangerousLocations_thenReturnsAllLocationsWithRepeatedLatLon() {
        // 🔹 Arrange
        List<Location> sampleLocations = Arrays.asList(
                new Location("10.0", "20.0", 1L),
                new Location("10.0", "20.0", 2L),
                new Location("11.0", "21.0", 3L),
                new Location("10.0", "20.0", 4L),
                new Location("11.0", "21.0", 5L),
                new Location("12.0", "22.0", 6L)
        );

        when(locationRepository.findAll()).thenReturn(sampleLocations);

        // 🔹 Act
        List<Location> result = locationService.obtainDangerousLocations(2);

        // 🔹 Assert
        assertThat(result).hasSize(6);
        long countRepeated = result.stream()
                .filter(loc -> loc.getALatitude().equals("10.0") && loc.getALongitude().equals("20.0"))
                .count();

        assertThat(countRepeated).isEqualTo(3);
        verify(locationRepository, times(1)).findAll();
    }
}
