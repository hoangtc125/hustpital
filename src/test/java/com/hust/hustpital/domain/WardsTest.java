package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WardsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wards.class);
        Wards wards1 = new Wards();
        wards1.setId("id1");
        Wards wards2 = new Wards();
        wards2.setId(wards1.getId());
        assertThat(wards1).isEqualTo(wards2);
        wards2.setId("id2");
        assertThat(wards1).isNotEqualTo(wards2);
        wards1.setId(null);
        assertThat(wards1).isNotEqualTo(wards2);
    }
}
