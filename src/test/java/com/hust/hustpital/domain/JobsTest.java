package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jobs.class);
        Jobs jobs1 = new Jobs();
        jobs1.setId("id1");
        Jobs jobs2 = new Jobs();
        jobs2.setId(jobs1.getId());
        assertThat(jobs1).isEqualTo(jobs2);
        jobs2.setId("id2");
        assertThat(jobs1).isNotEqualTo(jobs2);
        jobs1.setId(null);
        assertThat(jobs1).isNotEqualTo(jobs2);
    }
}
