package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.github.javafaker.Address;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.repositories.RSvelteUpUserProfile;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@NoArgsConstructor
@Profile("dev")
public class SSvelteUpUserProfileSetupOwningUser extends AOwningUserDataSetup
{

    @Autowired RSvelteUpUserProfile  svelteUpUserProfileRepository;
    @Autowired SImageS3 sImageS3;
    Resource imageResource = new ClassPathResource("/static/images/profileImg.txt");

    @Override
    public void setupEntity(OwningUserDataSetupDto  dto) throws NotSupportedException, IOException, InterruptedException {
        SvelteUpUserProfile profile = new SvelteUpUserProfile(dto.owningUser);
        Address address = this.fakeItTillYouMakeIt.address();
        Name name = fakeItTillYouMakeIt.name();

        profile.setAboutMe("This is an auto generated profile description");
        profile.setEmail("notifications@svelteup.app");
        profile.setUserProfileCountry("USA");
        profile.setUserProfileCity(address.city());
        profile.setUserProfileState(address.state());
        profile.setAddressLineOne(address.streetAddress());
        profile.setUserProfileZipCode(ThreadLocalRandom.current().nextInt(90000,99999));
        profile.setFirstName(name.firstName());
        profile.setLastName(name.lastName());
        profile.setPhoneNumberAreaCode(ThreadLocalRandom.current().nextInt(301,921));
        profile.setPhoneNumberPhoneNumber(ThreadLocalRandom.current().nextInt(1000000,9999999));
        profile.setOwningUsername(dto.owningUser.getUsername());

        profile.setProductReviewScoreCard(dto.owningUserProductReviewScoreCard);
        profile = svelteUpUserProfileRepository.save(profile);
        List<String> fileStringList = new ArrayList<>();
        fileStringList.add(StreamUtils.copyToString(imageResource.getInputStream(), StandardCharsets.UTF_8));
        sImageS3.put(dto.owningUser.getUsername(),fileStringList,profile.getSurrogateId(),SvelteUpUserProfile.class);
        dto.owningUserProfile  = profile;
    }
}
