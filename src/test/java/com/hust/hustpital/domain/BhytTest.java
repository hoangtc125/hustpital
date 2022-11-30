package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BhytTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bhyt.class);
        Bhyt bhyt1 = new Bhyt();
        bhyt1.setId("id1");
        Bhyt bhyt2 = new Bhyt();
        bhyt2.setId(bhyt1.getId());
        assertThat(bhyt1).isEqualTo(bhyt2);
        bhyt2.setId("id2");
        assertThat(bhyt1).isNotEqualTo(bhyt2);
        bhyt1.setId(null);
        assertThat(bhyt1).isNotEqualTo(bhyt2);
    }
}
