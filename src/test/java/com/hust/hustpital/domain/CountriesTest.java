package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Countries.class);
        Countries countries1 = new Countries();
        countries1.setId("id1");
        Countries countries2 = new Countries();
        countries2.setId(countries1.getId());
        assertThat(countries1).isEqualTo(countries2);
        countries2.setId("id2");
        assertThat(countries1).isNotEqualTo(countries2);
        countries1.setId(null);
        assertThat(countries1).isNotEqualTo(countries2);
    }
}
