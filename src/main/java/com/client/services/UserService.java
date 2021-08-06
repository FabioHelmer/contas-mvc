package com.client.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.domain.User;
import com.client.repository.UserRepository;
import com.client.util.PasswordUtil;
import com.client.util.Util;


@Service
public class UserService implements GenericService<User, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User entity) {
		try {

			if (entity.getId() == null || entity.getId().isEmpty())
				entity.setId(Util.toUUID());
			String hash = PasswordUtil.getSecureHash(entity.getSenha());
			entity.setSenha(hash);
			entity.setDataCadastro(new Date());
			return userRepository.save(entity);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User update(User entity) {
		User updatedUser = userRepository.save(entity);
		String hash = PasswordUtil.getSecureHash(updatedUser.getSenha());
		updatedUser.setSenha(hash);
		return updatedUser;
	}

	@Override
	public User getById(String id) {
		Optional<User> result = userRepository.findById(id);
		return result.get();
	}

	@Override
	public List<User> listAll() {
		List<User> results = userRepository.findAll();
		return results;
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);

	}

	@Override
	public void deleteById(String id) {
		User entity = getById(id);
		delete(entity);
	}

}
