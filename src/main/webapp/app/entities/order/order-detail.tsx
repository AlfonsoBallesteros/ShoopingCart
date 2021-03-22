import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderDetail = (props: IOrderDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Order [<b>{orderEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="count">Count</span>
          </dt>
          <dd>{orderEntity.count}</dd>
          <dt>
            <span id="subTotal">Sub Total</span>
          </dt>
          <dd>{orderEntity.subTotal}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{orderEntity.total}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{orderEntity.state ? 'true' : 'false'}</dd>
          <dt>Product</dt>
          <dd>{orderEntity.productId ? orderEntity.productId : ''}</dd>
          <dt>Sale</dt>
          <dd>{orderEntity.saleId ? orderEntity.saleId : ''}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ order }: IRootState) => ({
  orderEntity: order.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderDetail);
