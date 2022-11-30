import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PhongKham from './phong-kham';
import ChuyenKhoa from './chuyen-khoa';
import Countries from './countries';
import Cities from './cities';
import Districts from './districts';
import Wards from './wards';
import Ethnics from './ethnics';
import Jobs from './jobs';
import Bhyt from './bhyt';
import Patients from './patients';
import ThongTinVaoVien from './thong-tin-vao-vien';
import Banks from './banks';
import LichLamViec from './lich-lam-viec';
import Doctors from './doctors';
import LichHen from './lich-hen';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="phong-kham/*" element={<PhongKham />} />
        <Route path="chuyen-khoa/*" element={<ChuyenKhoa />} />
        <Route path="countries/*" element={<Countries />} />
        <Route path="cities/*" element={<Cities />} />
        <Route path="districts/*" element={<Districts />} />
        <Route path="wards/*" element={<Wards />} />
        <Route path="ethnics/*" element={<Ethnics />} />
        <Route path="jobs/*" element={<Jobs />} />
        <Route path="bhyt/*" element={<Bhyt />} />
        <Route path="patients/*" element={<Patients />} />
        <Route path="thong-tin-vao-vien/*" element={<ThongTinVaoVien />} />
        <Route path="banks/*" element={<Banks />} />
        <Route path="lich-lam-viec/*" element={<LichLamViec />} />
        <Route path="doctors/*" element={<Doctors />} />
        <Route path="lich-hen/*" element={<LichHen />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
