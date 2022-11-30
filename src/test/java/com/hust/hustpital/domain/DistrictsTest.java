package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Districts.class);
        Districts districts1 = new Districts();
        districts1.setId("id1");
        Districts districts2 = new Districts();
        districts2.setId(districts1.getId());
        assertThat(districts1).isEqualTo(districts2);
        districts2.setId("id2");
        assertThat(districts1).isNotEqualTo(districts2);
        districts1.setId(null);
        assertThat(districts1).isNotEqualTo(districts2);
    }
}
