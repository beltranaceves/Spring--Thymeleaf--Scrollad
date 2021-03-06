package es.udc.fi.dc.fd.test.unit.service.user;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class TestUserEntityService {

	public TestUserEntityService() {
        super();
    }
		
	@Autowired
	private UserService userService;
		
	private Set<String> scoreUSer= Sets.newHashSet();
	private Set<AdEntity> ads = new HashSet<AdEntity>(0);
	private Set<String> followed= Sets.newHashSet();
	
	private UserEntity createUserToTest(String username) {
		
		return new UserEntity(username, username, username, username, username, "city", 1, 3.0, 3);
		
	}
	
	private UserEntity createUserToTest2(String username) {
		
		return new UserEntity(username, username, username, username, username, "city",false, 1, 3.0, 3);
		
	}

	private UserEntity createUserToTest3(String username) {
	
	return new UserEntity(username, username, username, username, username, "city", 1, 3.0, scoreUSer, ads, followed );
	
	}
		
	
	@Test
	public void addTest() {
		
		User userEntity = userService.add(createUserToTest("viewer5")); 
		
		UserEntity expected = userService.findByUsername(userEntity.getUsername());
		
		UserEntity expectedById = userService.findById(userEntity.getId());
		
		Assert.assertEquals(expected.getUsername(),userEntity.getUsername());
		Assert.assertEquals(expectedById.getUsername(),userEntity.getUsername());
		Assert.assertEquals(expectedById.getFirstLastname(),userEntity.getFirstLastname());
		Assert.assertEquals(expected.getFirstLastname(),userEntity.getFirstLastname());
		Assert.assertEquals(expectedById.getSecondLastname(),userEntity.getSecondLastname());
		Assert.assertEquals(expected.getSecondLastname(),userEntity.getSecondLastname());
		Assert.assertEquals(expectedById.getCity(),userEntity.getCity());
		Assert.assertEquals(expected.getCity(),userEntity.getCity());
		


	}
	
	@Test
	public void addTest2() {
		
		User userEntity = userService.add(createUserToTest3("viewer5")); 
		
		UserEntity expected = userService.findByUsername(userEntity.getUsername());
		
		UserEntity expectedById = userService.findById(userEntity.getId());
		
		Assert.assertEquals(expected.getUsername(),userEntity.getUsername());
		Assert.assertEquals(expectedById.getUsername(),userEntity.getUsername());
	}
	
	@Test
	public void addTest3() {
		
		User userEntity = userService.add(createUserToTest2("viewer5")); 
		
		UserEntity expected = userService.findByUsername(userEntity.getUsername());
		
		UserEntity expectedById = userService.findById(userEntity.getId());
		
		Assert.assertEquals(expected.getUsername(),userEntity.getUsername());
		Assert.assertEquals(expectedById.getUsername(),userEntity.getUsername());
	}
	
	@Test
	public void findByUsernameWithoutUsername() {
		
		User userEntityLog = new UserEntity(); 
		
		UserEntity userLog  = userService.findByUsername(userEntityLog.getUsername());
		
		String userNameEmpty= "";
		
		Assert.assertEquals(userLog.getUsername(),userNameEmpty);
		
	}
	
	
	@Test
	public void rateUserTest() {
		
		User userEntity = userService.add(createUserToTest("viewer6")); 
		
		UserEntity userAd = userService.findByUsername(userEntity.getUsername());
		
		
		User userEntityLog = userService.add(createUserToTest("viewer7")); 
		
		UserEntity userLog  = userService.rateUser(userEntityLog.getUsername(), userAd.getUsername(), 3);
		
		Set<String> scoredUserList = userLog.getScored();
		
		
		Double expectedAverageCount = 3.0;
		
		
		Assert.assertTrue(scoredUserList.contains(userAd.getUsername()));
		Assert.assertEquals(userAd.getAverageScore(),expectedAverageCount);
	}
	
	@Test
	public void rateUserTestWithoutUserLog() {
		
		User userEntity = userService.add(createUserToTest("viewer6")); 
		
		UserEntity userAd = userService.findByUsername(userEntity.getUsername());
				
		
		User userEntityLog = new UserEntity(); 
		
		UserEntity userLog  = userService.rateUser(userEntityLog.getUsername(), userAd.getUsername(), 3);
		
		
		String userNameEmpty= "";
		
		
		Assert.assertEquals(userLog.getUsername(),userNameEmpty);
	}
	
	
	@Test
	public void followAndUnfollowUserTest() {
		
		User userEntity = userService.add(createUserToTest("viewer6")); 
		
		UserEntity userAd = userService.findByUsername(userEntity.getUsername());
		
			
		
		User userEntityLog = userService.add(createUserToTest("viewer7")); 
		
		
		//Probamos que se sigue correctamente
		
		UserEntity userLogToFollow  = userService.followAndUnfollow(userEntityLog.getUsername(), userAd.getUsername());
		
		Set<String> followersUserList = userLogToFollow.getFollowed();
				
		Assert.assertTrue(followersUserList.contains(userAd.getUsername()));
		
		
		//Probamos que se deja de seguir correctamente
		
		UserEntity userLogToUnfollow  = userService.followAndUnfollow(userEntityLog.getUsername(), userAd.getUsername());
		
		Set<String> followersUserListWithoutuserLogToFollow = userLogToUnfollow.getFollowed();
		
		Assert.assertFalse(followersUserListWithoutuserLogToFollow.contains(userAd.getUsername()));
	}
	
	
	
	@Test
	public void followUserTestWithoutUserLog() {
		
		User userEntity = userService.add(createUserToTest("viewer6")); 
		
		UserEntity userAd = userService.findByUsername(userEntity.getUsername());
				
		
		User userEntityLog = new UserEntity(); 
		
		UserEntity userLog  = userService.followAndUnfollow(userEntityLog.getUsername(), userAd.getUsername());
		
		
		String userNameEmpty= "";
		
		
		Assert.assertEquals(userLog.getUsername(),userNameEmpty);
	}
	
}