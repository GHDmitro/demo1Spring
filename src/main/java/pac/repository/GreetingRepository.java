package pac.repository;

import pac.entities.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by macbookair on 21.11.16.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long>{

    //с помощью SpringData можно добавить специфических методов
}
