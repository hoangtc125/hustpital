package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BanksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banks.class);
        Banks banks1 = new Banks();
        banks1.setId("id1");
        Banks banks2 = new Banks();
        banks2.setId(banks1.getId());
        assertThat(banks1).isEqualTo(banks2);
        banks2.setId("id2");
        assertThat(banks1).isNotEqualTo(banks2);
        banks1.setId(null);
        assertThat(banks1).isNotEqualTo(banks2);
    }
}
