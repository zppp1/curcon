package org.curcon.app.data;

import org.curcon.app.data.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

}
