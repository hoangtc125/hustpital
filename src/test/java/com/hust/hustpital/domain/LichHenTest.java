package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LichHenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichHen.class);
        LichHen lichHen1 = new LichHen();
        lichHen1.setId("id1");
        LichHen lichHen2 = new LichHen();
        lichHen2.setId(lichHen1.getId());
        assertThat(lichHen1).isEqualTo(lichHen2);
        lichHen2.setId("id2");
        assertThat(lichHen1).isNotEqualTo(lichHen2);
        lichHen1.setId(null);
        assertThat(lichHen1).isNotEqualTo(lichHen2);
    }
}
