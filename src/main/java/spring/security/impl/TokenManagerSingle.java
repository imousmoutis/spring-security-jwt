package spring.security.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import spring.model.User;
import spring.security.TokenInfo;
import spring.security.TokenManager;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again,
 * older token is invalidated.
 */
public class TokenManagerSingle implements TokenManager {
	
	private static final String CLAIM_KEY_USERID = "userId";
	private static final String CLAIM_KEY_FULLNAME = "fullName";
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	@Value("${jwt.secret}")
	private String secret;

	private Map<String, UserDetails> validUsers = new HashMap<>();

	/**
	 * This maps system users to tokens because equals/hashCode is delegated to User entity.
	 * This can store either one token or list of them for each user, depending on what you want to do.
	 * Here we store single token, which means, that any older tokens are invalidated.
	 */
	private Map<UserDetails, TokenInfo> tokens = new HashMap<>();

	@Override
	public TokenInfo createNewToken(UserContext userDetails) {
		String token;
		do {
			token = generateJWT(userDetails.getUser());
		} while (validUsers.containsKey(token));

		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		removeUserDetails(userDetails);
		UserDetails previous = validUsers.put(token, userDetails);
		if (previous != null) {
			System.out.println(" *** SERIOUS PROBLEM HERE - we generated the same token (randomly?)!");
			return null;
		}
		tokens.put(userDetails, tokenInfo);

		return tokenInfo;
	}

	private String generateJWT(User user) {
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(CLAIM_KEY_USERID, user.getId());
		claims.put(CLAIM_KEY_FULLNAME, user.getFullName());
		
		String token = Jwts.builder().setClaims(claims)
				.setId(UUID.randomUUID().toString())
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		return token;
	}
	
	private Date generateExpirationDate() {
		
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	@Override
	public void removeUserDetails(UserDetails userDetails) {
		TokenInfo token = tokens.remove(userDetails);
		if (token != null) {
			validUsers.remove(token.getToken());
		}
	}

	@Override
	public UserDetails removeToken(String token) {
		UserDetails userDetails = validUsers.remove(token);
		if (userDetails != null) {
			tokens.remove(userDetails);
		}
		return userDetails;
	}

	@Override
	public UserDetails getUserDetails(String token) {
		return validUsers.get(token);
	}

	@Override
	public Collection<TokenInfo> getUserTokens(UserDetails userDetails) {
		return Arrays.asList(tokens.get(userDetails));
	}

	@Override
	public Map<String, UserDetails> getValidUsers() {
		return Collections.unmodifiableMap(validUsers);
	}
}
