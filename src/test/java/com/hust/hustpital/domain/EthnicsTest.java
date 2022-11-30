package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EthnicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ethnics.class);
        Ethnics ethnics1 = new Ethnics();
        ethnics1.setId("id1");
        Ethnics ethnics2 = new Ethnics();
        ethnics2.setId(ethnics1.getId());
        assertThat(ethnics1).isEqualTo(ethnics2);
        ethnics2.setId("id2");
        assertThat(ethnics1).isNotEqualTo(ethnics2);
        ethnics1.setId(null);
        assertThat(ethnics1).isNotEqualTo(ethnics2);
    }
}
