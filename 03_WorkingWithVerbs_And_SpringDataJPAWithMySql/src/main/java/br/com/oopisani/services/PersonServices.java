package br.com.oopisani.services;
import br.com.oopisani.exception.ResourceNotFoundException;
import br.com.oopisani.model.Person;
import br.com.oopisani.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());


    @Autowired
    PersonRepository repository;

    private static ResourceNotFoundException get() {
        return new ResourceNotFoundException("No records found for this ID");
    }


    public List<Person> findAll() {
        logger.info("Finding all People!");
//
//        List<Person> persons = new ArrayList<Person>();
//
//        for(int i=0;i<8;i++) {
//            Person person = mockPerson(i);
//            persons.add(person);
//        }
//        return persons;
//    }
        return repository.findAll();
    }

//    public Person mockPerson(int i) {
//        Person person = new Person();
//        person.setId(counter.incrementAndGet());
//        person.setFirstName("Firstname " + i);
//        person.setLastName("Lastname " + i);
//        person.setAddress("Some Address in Brasil");
//        person.setGender("Male");
//        return person;
//    }

    // Mock
    public Person findById(Long id) {
        logger.info("Finding one Person!");

//        Person person = new Person();
//        person.setId(counter.incrementAndGet());
//        person.setFirstName("Leandro");
//        person.setLastName("Costa");
//        person.setAddress("Uberlândia - Minas Gerais - Brasil");
//        person.setGender("Male");
//        return person;
        return repository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("No records found for this ID"));
    }


    public Person create(Person person) {
        logger.info("Creating one Person!");
//        return person;
        return repository.save(person);
    }


    public Person update(Person person) {
        logger.info("Updating one Person!");
//        return person;
        Person entity = repository.findById(person.getId())
                .orElseThrow(() ->new ResourceNotFoundException("No records found for this ID"));
//        person.setId(counter.incrementAndGet()); O banco vai setar pra gente então não precisa passar o ID
//        entity.setFirstName("Firstname " + i);
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");

       Person entity = repository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
       repository.delete(entity);
    }
}
