package de.codecentric.resilient.address.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

import de.codecentric.resilient.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import de.codecentric.resilient.address.entity.Address;
import de.codecentric.resilient.dto.AddressDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address validateAddress(AddressDTO addressDTO) {

        Address addressToSearch = new Address(addressDTO.getCountry(), addressDTO.getCity(), addressDTO.getPostcode(),
            addressDTO.getStreet(), addressDTO.getStreetNumber());

        //@formatter:off
        ExampleMatcher matcher =
            ExampleMatcher.matching()
                    .withMatcher("country", startsWith().ignoreCase())
                    .withMatcher("postcode", startsWith().ignoreCase())
                    .withMatcher("street", contains().ignoreCase())
                    .withMatcher("streetNumber", contains().ignoreCase())
                    .withMatcher("city", contains().ignoreCase());

        //@formatter:on
        Example<Address> searchExample = Example.of(addressToSearch, matcher);

        return addressRepository.findOne(searchExample);

    }
}
