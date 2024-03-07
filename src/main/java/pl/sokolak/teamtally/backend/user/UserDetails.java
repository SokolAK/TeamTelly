//package pl.sokolak.teamtally.backend.user;
//
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collections;
//import java.util.List;
//
//@Getter
//public class UserDetails {
//
//    private String username;
//    private List<String> authorities = Collections.emptyList();
//
//    public UserDetails(org.springframework.security.core.userdetails.UserDetails userDetails) {
//        if (userDetails != null) {
//            username = userDetails.getUsername();
//            authorities = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .map(String::toString)
//                    .map(String::toLowerCase)
//                    .toList();
//        }
//    }
//
//    public boolean isAdmin() {
//        return authorities.stream().anyMatch(auth -> auth.equalsIgnoreCase("ROLE_ADMIN"));
//    }
//}
