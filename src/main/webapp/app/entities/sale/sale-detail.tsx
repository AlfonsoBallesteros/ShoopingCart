import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sale.reducer';
import { ISale } from 'app/shared/model/sale.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISaleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SaleDetail = (props: ISaleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { saleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Sale [<b>{saleEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{saleEntity.code}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{saleEntity.date ? <TextFormat value={saleEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="medioPago">Medio Pago</span>
          </dt>
          <dd>{saleEntity.medioPago}</dd>
          <dt>
            <span id="subTotal">Sub Total</span>
          </dt>
          <dd>{saleEntity.subTotal}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{saleEntity.total}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{saleEntity.state ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/sale" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale/${saleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sale }: IRootState) => ({
  saleEntity: sale.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SaleDetail);
