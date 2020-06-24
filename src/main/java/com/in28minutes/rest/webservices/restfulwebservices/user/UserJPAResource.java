package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.user.exception.UserNotFoundException;

@RestController
public class UserJPAResource {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		final Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
		
		EntityModel<User> model = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		return model;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		final User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		/*
		final User deletedUser = service.deleteById(id);
		if (deletedUser == null) {
			throw new UserNotFoundException("User not found: " + id );
		}
		*/
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{userId}/posts")
	public List<Post> retrieveAllPosts(@PathVariable("userId") Integer userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found with id: " + userId + " to get posts.");
		}
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{userId}/posts")
	public ResponseEntity<User> createPost(@PathVariable("userId") Integer userId, @Valid @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found with id: " + userId + " to get posts.");
		}

		post.setUser(userOptional.get());
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(post.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/jpa/users/{userId}/posts/{postId}")
	public EntityModel<Post> retrievePost(@PathVariable("userId") int userId, @PathVariable("postId") int postId) {
		final Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found with id: " + userId);
		}
		
		Optional<Post> postOptional = postRepository.findById(postId);
		if (!postOptional.isPresent()) {
			throw new RuntimeException("Post not found with post id: " + postId);
		}
		User user = userOptional.get();
		EntityModel<Post> model = EntityModel.of(postOptional.get());
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllPosts(user.getId()));
		model.add(linkTo.withRel("user-all-posts"));
		return model;
	}
}
