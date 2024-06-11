package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.additional.log.template.AbstractTemplate;
import shoppingmall.project.additional.log.trace.LogTrace;
import shoppingmall.project.additional.log.trace.TraceId;
import shoppingmall.project.additional.log.trace.TraceStatus;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;



    @Transactional(readOnly = true)
    public User findByLoginId(String loginId) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
                //호출부에서 null 반환시 처리하는 로직 필요
        return optionalUser.orElse(null);

    }

    /**
     * 회원 가입시 사용자를 저장하는 로직
     * @param userForm 회원 가입한 사용자의 정보
     * @return 회원 가입한 사용자 객체
     */
    public User createSaveUser(UserForm userForm) {
        Address address = new Address(userForm.getZipcode(), userForm.getCity(), userForm.getStreet());

        User user = new User(
                userForm.getLoginId(),
                userForm.getName(),
                userForm.getAge(),
                userForm.getEmail(),
                userForm.getPassword(),
                address,
                Tier.NORMAL);
        return userRepository.save(user);
    }

    /**
     * 회원가입시 아이디가 중복인지 검사해주는 로직
     * @param loginId 검사할 아이디
     * @return 중복시 true 중복 아닐시 false 반환
     */
    public boolean existsId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    /**
     * 테스트에 사용되는 서비스 로직
     */
    public void clear() {
        userRepository.deleteAll();
    }


    /**
     * 로그인 로직
     * @return 비밀번호가 틀릴 시 null 반환
     */
    @Transactional(readOnly = true)
    public User login(String id, String password) {
        return userRepository.findByLoginId(id)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }


    /**
     * 사용자가 결제한 금액을 누적해 티어를 바꿔주기 위한 로직
     * @param user 결제한 사용자 객체
     * @param totalPrice 사용자가 결제한 금액
     */
    @Transactional(readOnly = true)
    public void addAccumulatedAmount(User user, int totalPrice) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User findUser = optionalUser.orElseThrow(null);

        findUser.addAmount(totalPrice);

        if (findUser.getAccumulatedAmount() <= 1000000) {
        findUser.upgradeTier(findUser.getAccumulatedAmount());
        }
    }


    /**
     * 결제할 때 사용자의 tier에 따라 할인을 위해 tier를 반환하는 로직
     * @param userId 사용자의 id
     * @return 사용자의 tier
     */
    @Transactional(readOnly = true)
    public Tier findUserTier(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User findUser = user.orElseThrow(null);
        return findUser.getTier();
    }



}
